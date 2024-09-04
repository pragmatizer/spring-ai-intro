package guru.springframework.springaiintro.services;

import guru.springframework.springaiintro.model.*;

public interface OpenAIService {
    String getAnswer(String question);
    Answer getAnswer(Question question);
    Answer getCapital(GetCapitalRequest getCapitalRequest);
    Answer getCapitalWithInfo(GetCapitalRequest getCapitalRequest);
    Answer getCapitalWithJson(GetCapitalRequest getCapitalRequest);
    GetCapitalResponse getCapitalWithJsonSchema(GetCapitalRequest getCapitalRequest);
    GetCapitalFullResponse getCapitalWithAssignment(GetCapitalRequest getCapitalRequest);

}
