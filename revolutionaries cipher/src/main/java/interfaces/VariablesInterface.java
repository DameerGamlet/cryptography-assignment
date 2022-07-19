package interfaces;

import de.Decryption;
import en.Encryption;
import parsing.Write;
import table.TableOperation;
import var.Variables;

public interface VariablesInterface {
    Variables variables = new Variables();
    Encryption encryption = new Encryption();
    Decryption decryption = new Decryption();
    Write write = new Write();
    TableOperation operationWithTable = new TableOperation();
}
