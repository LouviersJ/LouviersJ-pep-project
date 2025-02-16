package Service;
import Model.Account;
import DAO.AccountDAO;
import java.util.List;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO;
    }

    public AccountService(AccountDAO accountDAO){
    this.accountDAO = accountDAO;
    }

    public Account checkAccount(Account account){
        return accountDAO.checkAccount(account);
    }

    public Account addAccount(Account account){
        return accountDAO.addAccount(account);
    }

    public List<Message> getAccountMessages(int account_id){
        return accountDAO.getAccountMessages(account_id);
    }
}
