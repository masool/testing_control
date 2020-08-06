package bccontrol;

import net.corda.client.rpc.CordaRPCClient;
import net.corda.client.rpc.CordaRPCConnection;
import net.corda.core.messaging.CordaRPCOps;
import net.corda.core.utilities.NetworkHostAndPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CordaClient {
	public CordaClient() {
	}
	public CordaRPCOps getCordaRPCOps(String node, String user, String password) {
      /*  if (args.length != 3) {
            throw new IllegalArgumentException("Usage: TemplateClient <node address> <username> <password>");
        }
      */
        final NetworkHostAndPort nodeAddress = NetworkHostAndPort.parse(node);
        String username = user;
        String passwd = password;
        final CordaRPCClient client = new CordaRPCClient(nodeAddress);
        final CordaRPCConnection connection = client.start(username, passwd);
        final CordaRPCOps cordaRPCOperations = connection.getProxy();
		return cordaRPCOperations;
	}
}