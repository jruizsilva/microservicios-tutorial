package userservice.models;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Car {
    private String marca;
    private String modelo;
    private Long userId;
}
