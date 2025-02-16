package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        //create new account
        app.post("/register", this :: postAccountHandler);
        app.post("/login", this :: loginAccountHandler);
        app.post("/messages", this :: postMessageHandler);
        app.get("/messages", this :: getMessagesHandler);
        app.get("/messages/{message_id}", this :: getMessageHandler);
        app.delete("/messages/{message_id}", deleteMessageHandler);
        app.patch("/messages/{message_id}", updateMessageHandler);
        app.get("/accounts/{account_id}/messages", getAccountMessagesHandler);
        app.start(8080);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount!=null){
            ctx.json(mapper.writeValueAsString(addedAccount)).status(200);
        }else{
            ctx.status(400);
        }
    }

    private void loginAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account checkedAccount = accountService.checkAccount(account);
        if(checkedAccount != null){
            ctx.json(mapper.writeValueAsString(checkedAccount)).status(200);
        }else{
            ctx.status(401);
        }
    }

    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage != null){
            ctx.json(mapper.writeValueAsString(addedMessage)).status(200);
        }else{
            ctx.status(400);
        }
    }

    private void getMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages).status(200);
    }

    private void getMessageHandler(Context ctx) {
        Message message = messageService.getMessage(ctx.pathParam("message_id"));
        ctx.json(message).status(200);
    }
    private void deleteMessageHandler(Context ctx) {
        Message message = messageService.deleteMessage(ctx.pathParam("message_id"));
        ctx.json(message).status(200);
    }

    private void updateMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message updatedMessage = messageService.updateMessage(message, ctx.pathParam("message_id"));
        if(updatedMessage != null){
            ctx.json(mapper.writeValueAsString(updatedMessage)).status(200);
        }else{
            ctx.status(400);
        }
    }

    private void getAccountMessagesHandler(Context ctx) {
        List<Message> messages = accountService.getAccountMessages();
        ctx.json(messages).status(200);
    }
}