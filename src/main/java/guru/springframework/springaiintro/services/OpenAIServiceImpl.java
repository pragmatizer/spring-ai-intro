package guru.springframework.springaiintro.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.springaiintro.model.*;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OpenAIServiceImpl implements OpenAIService{
    private final ChatClient chatClient;

    @Autowired
    private ObjectMapper objectMapper;

    public OpenAIServiceImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public Answer getAnswer(Question question) {
        System.out.println("I was called");

        return new Answer(
                this.chatClient.prompt()
                .user(question.question())
                .call()
                .content()
        );
    }

    //@Value("classpath:templates/get-capital-prompt-json-schema.st")
    //private Resource getCapitalPromptJsonSchema;
    @Override
    public GetCapitalFullResponse getCapitalWithAssignment(GetCapitalRequest getCapitalRequest) {
        //BeanOutputParser<GetCapitalFullResponse> parser = new BeanOutputParser<>(GetCapitalFullResponse.class);
        BeanOutputConverter<GetCapitalFullResponse> parser = new BeanOutputConverter<>(GetCapitalFullResponse.class);
        String format = parser.getFormat();

        System.out.println("format: \n" + format);

        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPromptJsonSchema);
        Prompt prompt = promptTemplate.create(Map.of(
                "stateOrCountry", getCapitalRequest.stateOrCountry(),
                "format", format
        ));
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();

        System.out.println(response.getResult().getOutput().getContent());
        return parser.parse(response.getResult().getOutput().getContent());

    }

    @Value("classpath:templates/get-capital-prompt-json-schema.st")
    private Resource getCapitalPromptJsonSchema;
    @Override
    public GetCapitalResponse getCapitalWithJsonSchema(GetCapitalRequest getCapitalRequest) {
        BeanOutputParser<GetCapitalResponse> parser = new BeanOutputParser<>(GetCapitalResponse.class);
        String format = parser.getFormat();
        System.out.println("format: \n" + format);

        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPromptJsonSchema);
        Prompt prompt = promptTemplate.create(Map.of(
                "stateOrCountry", getCapitalRequest.stateOrCountry(),
                "format", format
        ));
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();

        System.out.println(response.getResult().getOutput().getContent());
        return parser.parse(response.getResult().getOutput().getContent());

    }

    @Value("classpath:templates/get-capital-prompt-json.st")
    private Resource getCapitalPromptJson;
    @Override
    public Answer getCapitalWithJson(GetCapitalRequest getCapitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPromptJson);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();

        System.out.println(response.getResult().getOutput().getContent());
        String responseString;
        try {
            JsonNode jsonNode = objectMapper.readTree(response.getResult().getOutput().getContent());
            responseString = jsonNode.get("answer").asText();
        } catch(JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        //return new Answer(response.getResult().getOutput().getContent());
        return new Answer(responseString);
    }

    @Value("classpath:templates/get-capital-with-info.st")
    private Resource getCapitalPromptWithInfo;

    @Override
    public Answer getCapitalWithInfo(GetCapitalRequest getCapitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPromptWithInfo);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
        return new Answer(chatClient.prompt(prompt).call().content());
    }



    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPrompt;
    @Override
    public Answer getCapital(GetCapitalRequest getCapitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
        return new Answer(chatClient.prompt(prompt).call().content());
    }


    @Override
    public String getAnswer(String question) {
        return this.chatClient.prompt()
                .user(question)
                .call()
                .content();
    }

}
