package site.minnan.linkserver.entites;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseEntity<T> {

    private String code;

    private String message;

    private T data;

    public ResponseEntity(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
