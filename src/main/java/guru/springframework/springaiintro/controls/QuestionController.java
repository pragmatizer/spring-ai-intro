package guru.springframework.springaiintro.controls;

import guru.springframework.springaiintro.model.*;
import guru.springframework.springaiintro.services.OpenAIService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {
    private final OpenAIService openAIService;

    public QuestionController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/capitalWithAssignment")
    public GetCapitalFullResponse getCapitalWithAssignment(@RequestBody GetCapitalRequest getCapitalRequest) {
        return openAIService.getCapitalWithAssignment(getCapitalRequest);
    }


    @PostMapping("/capitalWithJsonSchema")
    public GetCapitalResponse getCapitalWithJsonSchema(@RequestBody GetCapitalRequest getCapitalRequest) {
        return openAIService.getCapitalWithJsonSchema(getCapitalRequest);
    }

    @PostMapping("/capitalWithJson")
    public Answer askCapitalWithJson(@RequestBody GetCapitalRequest getCapitalRequest) {
        return openAIService.getCapitalWithJson(getCapitalRequest);
    }

    @PostMapping("/capitalWithInfo")
    public Answer getCapitalWithInfo(@RequestBody GetCapitalRequest getCapitalRequest) {
        return openAIService.getCapitalWithInfo(getCapitalRequest);
    }

    @PostMapping("/capital")
    public Answer getCapital(@RequestBody GetCapitalRequest getCapitalRequest) {
        return openAIService.getCapital(getCapitalRequest);
    }

    @PostMapping("/ask")
    public Answer askQuestion(@RequestBody Question question) {
        return openAIService.getAnswer(question);
    }

}
