package fr.jadde;

import fr.jadde.database.entity.TeamEntity;
import fr.jadde.domain.command.team.CreateTeamCommand;
import fr.jadde.resource.TeamResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.callback.QuarkusTestBeforeEachCallback;
import io.quarkus.test.junit.callback.QuarkusTestMethodContext;
import io.smallrye.mutiny.Uni;
import org.assertj.core.api.Assertions;
import org.hamcrest.CustomMatcher;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;


@QuarkusTest
@TestHTTPEndpoint(TeamResource.class)
public class ExampleResourceTest implements QuarkusTestBeforeEachCallback {

    @Test
    public void testCreateTeam() {
//        final TeamEntity responseEntity = given()
//                .body(new CreateTeamCommand("myTeam"))
//                .contentType("application/json")
//                .when().post()
//                .then()
//                .extract()
//
//                .body()
//                .as(TeamEntity.class);
//        Assertions.assertThat(responseEntity.id).isGreaterThan(0);
//        Assertions.assertThat(responseEntity.getName()).isEqualTo("myTeam");
    }

    @Test
    public void testGetAllTeams() {

        final Collection<?> responseEntity = given()
                .contentType("application/json")
                .when().get()
                .then()
                .extract()
                .body()
                .as(Collection.class);
        System.out.println("bite");
    }

    @Override
    public void beforeEach(final QuarkusTestMethodContext context) {
        final TeamEntity firstTeam =new TeamEntity();
        final TeamEntity secondTeam =new TeamEntity();

        firstTeam.setName("dorian");
        secondTeam.setName("dorian");

        Uni.combine().all().unis(firstTeam.persistAndFlush(), secondTeam.persistAndFlush()).asTuple()
                .await().indefinitely();
    }
}