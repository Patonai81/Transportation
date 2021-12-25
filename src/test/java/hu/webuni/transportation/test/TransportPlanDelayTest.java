package hu.webuni.transportation.test;

import hu.webuni.transportation.config.InitDb;
import hu.webuni.transportation.dto.RegisterDelayDTO;
import hu.webuni.transportation.model.Milestone;
import hu.webuni.transportation.model.Ordinal;
import hu.webuni.transportation.model.Section;
import hu.webuni.transportation.model.TransportPlan;
import hu.webuni.transportation.repository.MilestoneRepository;
import hu.webuni.transportation.repository.SectionRepository;
import hu.webuni.transportation.repository.TransportPlanRepository;
import hu.webuni.transportation.service.SectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("delay")
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransportPlanDelayTest {

    @Autowired
    WebTestClient webClient;

    @Autowired
    InitDb initDb;

    @Autowired
    MilestoneRepository milestoneRepository;

    @Autowired
    TransportPlanRepository transportPlanRepository;

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    SectionService sectionService;

    @BeforeEach
    public void initTest() {
        initDb.createTestData();
    }

    static final int MINUTES_PER_HOUR = 60;
    static final int SECONDS_PER_MINUTE = 60;
    static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;

    @Test
    public void testNotExistingMilestone() {

        RegisterDelayDTO registerDelayDTO = new RegisterDelayDTO(null,22l,12l);
        webClient.post()
                .uri("api/transportPlans/666/delay")
                .bodyValue(registerDelayDTO)
                .exchange()
                .expectStatus()
                .isNotFound();

    }

    @Test
    public void testNotExistingTransportPlan() {

        List<Milestone> milestones = milestoneRepository.findAll();
        RegisterDelayDTO registerDelayDTO = new RegisterDelayDTO(null,milestones.get(0).getId(),12l);
        webClient.post()
                .uri("api/transportPlans/666/delay")
                .bodyValue(registerDelayDTO)
                .exchange()
                .expectStatus()
                .isNotFound();

    }

    @Test
    public void testOrphanMilestone() {

        TransportPlan transportPlan = transportPlanRepository.findAll().get(0);
        List<Milestone> milestones = milestoneRepository.getOrphanMilestones();
        RegisterDelayDTO registerDelayDTO = new RegisterDelayDTO(null,milestones.get(0).getId(),12l);
        webClient.post()
                .uri("api/transportPlans/"+transportPlan.getId()+"/delay")
                .bodyValue(registerDelayDTO)
                .exchange()
                .expectStatus()
                .isBadRequest();

    }

    @Test
    public void testUpdateWithFromMilestoneDelay() {

        Milestone milestone = milestoneRepository.getOrdinalMilestones(Ordinal.FROM).get(0);
        TransportPlan transportPlan = milestone.getTransportPlan();
        Section section = sectionRepository.getSectionByMilestoneId(milestone.getId()).get();
        Milestone toMilestone = section.getToMilestone();

        LocalDateTime originalTimeFromSection = milestone.getPlannedTime();
        LocalDateTime originalTimeToSection = toMilestone.getPlannedTime();
        long delay = 12l;

        RegisterDelayDTO registerDelayDTO = new RegisterDelayDTO(transportPlan.getId(), milestone.getId(),delay);
        webClient.post()
                .uri("api/transportPlans/"+transportPlan.getId()+"/delay")
                .bodyValue(registerDelayDTO)
                .exchange()
                .expectStatus()
                .isOk();

        Milestone milestoneAfterUpdate = milestoneRepository.findById(milestone.getId()).get();
        LocalDateTime adjustedTimeFromSection = milestoneAfterUpdate.getPlannedTime();
        Duration duration = Duration.between(originalTimeFromSection,adjustedTimeFromSection);
        long minutes = ((duration.getSeconds() % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
        assertThat(minutes).isEqualTo(delay);

        Milestone toMilestoneAfterUpdate = milestoneRepository.findById(toMilestone.getId()).get();
        LocalDateTime adjustedTimeToSection = toMilestoneAfterUpdate.getPlannedTime();
        duration = Duration.between(originalTimeToSection,adjustedTimeToSection);
        minutes = ((duration.getSeconds() % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
        assertThat(minutes).isEqualTo(delay);

        System.out.println(milestone);
        System.out.println(toMilestone);

    }

    @Test
    public void testUpdateWithToMilestoneDelay() {

        Milestone toMilestone = milestoneRepository.getOrdinalMilestones(Ordinal.TO).get(0);
        TransportPlan transportPlan = toMilestone.getTransportPlan();

        Section section = sectionService.getNextSectionByMilestoneId(toMilestone.getId()).get();
        Milestone fromMilestone = section.getFromMilestone();

        LocalDateTime originalTimeFromSection = toMilestone.getPlannedTime();
        LocalDateTime originalTimeToSection = fromMilestone.getPlannedTime();
        long delay = 44l;

        RegisterDelayDTO registerDelayDTO = new RegisterDelayDTO(transportPlan.getId(), toMilestone.getId(),delay);
        webClient.post()
                .uri("api/transportPlans/"+transportPlan.getId()+"/delay")
                .bodyValue(registerDelayDTO)
                .exchange()
                .expectStatus()
                .isOk();

        Milestone milestoneAfterUpdate = milestoneRepository.findById(toMilestone.getId()).get();
        LocalDateTime adjustedTimeFromSection = milestoneAfterUpdate.getPlannedTime();
        Duration duration = Duration.between(originalTimeFromSection,adjustedTimeFromSection);
        long minutes = ((duration.getSeconds() % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
        assertThat(minutes).isEqualTo(delay);

        Milestone toMilestoneAfterUpdate = milestoneRepository.findById(fromMilestone.getId()).get();
        LocalDateTime adjustedTimeToSection = toMilestoneAfterUpdate.getPlannedTime();
        duration = Duration.between(originalTimeToSection,adjustedTimeToSection);
        minutes = ((duration.getSeconds() % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
        assertThat(minutes).isEqualTo(delay);

    }

    @Test
    public void testCheckProfitAfterUpdate() {

        Milestone toMilestone = milestoneRepository.getOrdinalMilestones(Ordinal.TO).get(0);
        TransportPlan transportPlan = toMilestone.getTransportPlan();

        long delay = 44l;

        RegisterDelayDTO registerDelayDTO = new RegisterDelayDTO(transportPlan.getId(), toMilestone.getId(),delay);
        webClient.post()
                .uri("api/transportPlans/"+transportPlan.getId()+"/delay")
                .bodyValue(registerDelayDTO)
                .exchange()
                .expectStatus()
                .isOk();

      TransportPlan transportPlanAfterUpdate = transportPlanRepository.findById(transportPlan.getId()).get();
        assertThat(transportPlanAfterUpdate.getProfit().doubleValue()).isEqualTo((97.5*transportPlan.getProfit().doubleValue())/100);
    }
}