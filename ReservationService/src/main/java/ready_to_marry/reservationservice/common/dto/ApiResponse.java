package ready_to_marry.reservationservice.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;
    private Meta meta;
    private List<ErrorDetail> errors;

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> res = new ApiResponse<>();
        res.code = 0;
        res.message = "OK";
        res.data = data;
        return res;
    }

    public static <T> ApiResponse<T> success(T data, Meta meta) {
        ApiResponse<T> res = success(data);
        res.meta = meta;
        return res;
    }

    public static <T> ApiResponse<T> error(int code, String message, List<ErrorDetail> errors) {
        ApiResponse<T> res = new ApiResponse<>();
        res.code = code;
        res.message = message;
        res.errors = errors;
        return res;
    }
}

