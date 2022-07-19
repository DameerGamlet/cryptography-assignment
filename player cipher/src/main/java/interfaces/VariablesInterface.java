package interfaces;

import de.Decryption;
import en.Encryption;
import parsing.Write;
import string_operation.StringOperation;
import table_operation.TableOperation;
import var.Variables;

public interface VariablesInterface {
    Variables variables = new Variables();
    Encryption encryption = new Encryption();
    Decryption decryption = new Decryption();
    TableOperation operationWithTable = new TableOperation();
    Write write = new Write();
    StringOperation operationWithString = new StringOperation();
}
