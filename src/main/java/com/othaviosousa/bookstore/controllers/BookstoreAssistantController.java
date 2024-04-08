package com.othaviosousa.bookstore.controllers;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/bookstore")
public class BookstoreAssistantController {

    private final OpenAiChatClient chatClient;

    public BookstoreAssistantController(OpenAiChatClient chatClient) {
        this.chatClient = chatClient;
    }

    // @GetMapping("/informations")
    // public String bookstoreChat(
    // @RequestParam(value = "message", defaultValue = "Quais são os bestsellers
    // mais vendidos da década?") String message) {
    // return chatClient.call(message);
    // }

    @GetMapping("/informations")
    public ChatResponse bookstoreChat(
            @RequestParam(value = "message", defaultValue = "Quais são os bestsellers mais vendidos da década?") String message) {
        return chatClient.call(new Prompt(message));
    }

    @GetMapping("/reviews")
    public String getBookReview(@RequestParam(value = "book", defaultValue = "1984") String book) {
        PromptTemplate prompt = new PromptTemplate("""
                Me forneça um resumo sobre o livro {book} e uma biografia do seu autor
                """);

        prompt.add("book", book);

        return this.chatClient.call(prompt.create()).getResult().getOutput().getContent();
    }

}
