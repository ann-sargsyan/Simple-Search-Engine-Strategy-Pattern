package org.search.person;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public record Person(
        String firstName,
        String lastName,
        String email) {

}
