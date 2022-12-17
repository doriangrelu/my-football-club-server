package fr.jadde.domain.model;

import java.util.Set;

public record Team(String id,
                   String name,
                   UserInformation owner,
                   Set<UserInformation> members
) {


}
