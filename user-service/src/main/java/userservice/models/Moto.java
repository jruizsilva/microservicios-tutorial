package userservice.models;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Moto {
    private String marca;
    private String modelo;
    private Long userId;
}
