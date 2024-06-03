package com.mydiary.my_diary_server.test2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Service
public class GptServiceImpl implements GptService {
	 @Value("${openai.secret-key}")
    private String apiKey;

    public JsonNode callChatGpt(String userMsg) throws JsonProcessingException {
        final String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("model", "gpt-4");

        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", userMsg);
        messages.add(userMessage);

        Map<String, String> assistantMessage = new HashMap<>();
        assistantMessage.put("role", "system");
        assistantMessage.put("content", "너는 친절한 AI야");
        messages.add(assistantMessage);

        bodyMap.put("messages", messages);

        String body = objectMapper.writeValueAsString(bodyMap);

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        return objectMapper.readTree(response.getBody());
    }

    @Override
    public String getAssistantMsg(String userMsg) throws JsonProcessingException {
        JsonNode jsonNode = callChatGpt(userMsg);
        String content = jsonNode.path("choices").get(0).path("message").path("content").asText();

        return content;
    }
}

/*
@Slf4j
@Service
public class ChatGPTService {

    @Value("${openai.secret-key}")
    private String apiKey;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    final String assistant_id = "asst_0cdgfv5WWsYDlWYHKvhFWi7o";
    String thread_id = "thread_iUbSh6bRuyYtwolIQJ1q9YmG";

    public ChatGPTService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }
    public ChatGPTService(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public void run() {

        final String assistant_id = "asst_0cdgfv5WWsYDlWYHKvhFWi7o";

        //LOAD YOUR API KEY
        Properties properties = new Properties();

        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
                return;
            }

            properties.load(input);

            ChatGPTService client = new ChatGPTService(apiKey);
            //AssistantResponseDTO assistantResponse = client.createAssistant("You are an expert in geography, be helpful and concise.");

            ThreadResponseDTO threadResponse = client.createThread();
            log.info("thread id : " + threadResponse.id());
            thread_id = threadResponse.id();

            MessageResponseDTO messageResponse = client.sendMessage(thread_id, "user", "What is the capital of Italy?");

            client.runMessage(thread_id, assistant_id);
            //Retrieve and handle responses
            MessagesListResponseDTO response = client.getMessages(thread_id);
            response.printAllMessagesText();


        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MessageResponseDTO sendMessageTest(String role, String content) throws Exception {
        final String assistant_id = "asst_0cdgfv5WWsYDlWYHKvhFWi7o";
        ChatGPTService client = new ChatGPTService(apiKey);

        MessageResponseDTO messageResponse = client.sendMessage(thread_id, "user", content);
        client.runMessage(thread_id, assistant_id);

        return messageResponse;
    }
    
    private String post(String url, Object body) throws Exception {
        String jsonBody = objectMapper.writeValueAsString(body);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .header("OpenAI-Beta", "assistants=v1")  // Add this line
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public AssistantResponseDTO createAssistant(String initialPrompt) throws Exception {
        String url = "https://api.openai.com/v1/assistants";
        AssistantRequestDTO dto = new AssistantRequestDTO("gpt-3.5-turbo-0125", initialPrompt);
        String response = post(url, dto);
        return objectMapper.readValue(response, AssistantResponseDTO.class);
    }

    public ThreadResponseDTO createThread() throws Exception {
        String url = "https://api.openai.com/v1/threads";

        String response = post(url, "");
        return objectMapper.readValue(response, ThreadResponseDTO.class);
    }

    public MessageResponseDTO sendMessage(String threadId, String role, String content) throws Exception {
        String url = "https://api.openai.com/v1/threads/" + threadId + "/messages";
        MessageDTO dto = new MessageDTO(role, content);

        String response = post(url, dto);
        return objectMapper.readValue(response, MessageResponseDTO.class);
    }


    public RunResponseDTO runMessage(String threadId, String assistantId) throws Exception {
        String url = "https://api.openai.com/v1/threads/" + threadId + "/runs";
        RunRequestDTO dto = new RunRequestDTO(assistantId);

        String response = post(url, dto);
        return objectMapper.readValue(response, RunResponseDTO.class);
    }

    public MessagesListResponseDTO getMessages(String threadId) throws Exception {
        String url = "https://api.openai.com/v1/threads/" + threadId + "/messages";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + apiKey)
                .header("OpenAI-Beta", "assistants=v1")
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(response.body(), MessagesListResponseDTO.class);
        // Assuming the response is a JSON array of MessageResponseDTO
        //return objectMapper.readValue(response.body(), new TypeReference<List<MessageResponseDTO>>() {});
    }


    
    // 테스트용 함수 ( 로그인 기능 갖춰지면 삭제 )
    public MessagesListResponseDTO getMessagesTest() throws Exception {
        String url = "https://api.openai.com/v1/threads/" + thread_id + "/messages";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + apiKey)
                .header("OpenAI-Beta", "assistants=v1")
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(response.body(), MessagesListResponseDTO.class);
        // Assuming the response is a JSON array of MessageResponseDTO
        //return objectMapper.readValue(response.body(), new TypeReference<List<MessageResponseDTO>>() {});
    
    }
    
    
}

*/
    