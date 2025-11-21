package com.example.bankapplication.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookRequest {

    @Size(min = 1, max = 30)
    private String title;

    @Size(min = 1, max = 30)
    private String author;

    @Size(min = 1, max = 30)
    private String category;

    @Min(value = 1)
    @Max(value = 5)
    private int rating;


}
