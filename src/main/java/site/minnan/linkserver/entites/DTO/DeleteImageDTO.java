package site.minnan.linkserver.entites.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class DeleteImageDTO implements Serializable {

    @NotNull
    private Integer id;
}
