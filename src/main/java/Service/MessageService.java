package Service;
import Model.Message;
import DAO.MessageDAO;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
    this.messageDAO = messageDAO;
    }

    public Message addMessage(Message message){
        return messageDAO.addMessage(message);
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessage(int message_id){
        return messageDAO.getMessage(message_id);
    }

    public Message deleteMessage(int message_id) {
        return messageDAO.deleteMessage(message_id);
    }

    public Message updateMessage(Message message, int message_id){
        return messageDAO.updatedMessage(message, message_id);
    }
}

