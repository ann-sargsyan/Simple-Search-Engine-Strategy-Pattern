package org.search.person;

import lombok.Builder;

@Builder
public record Person(
        String firstName,
        String lastName,
        String email) {

}
