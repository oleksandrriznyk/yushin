package ua.library.web.command;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

public class CommandContainer {
	private static final Logger LOG = Logger.getLogger(CommandContainer.class);

	private static Map<String, Command> commands = new TreeMap<String, Command>();

	static {
		// common commands
		commands.put("login", new LoginCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("viewSettings", new ViewSettingsCommand());
		commands.put("search", new SearchCommand());
		commands.put("viewCreateUser", new ViewCreateUserCommand());
		commands.put("createUser", new CreateUserCommand());
		

		// client commands
		commands.put("viewMain", new ViewMainCommand());
		commands.put("sort", new SortBooksByNameCommand());
		commands.put("book", new BookCommand());
		
		// librarian commands
		commands.put("viewMainLibrarian", new ViewMainLibrarianCommand());

		// admin commands
		commands.put("createBook", new CreateBookCommand());
		commands.put("viewMainAdmin", new ViewMainAdminCommand());

		LOG.debug("Command container was successfully initialized");
		LOG.trace("Number of commands --> " + commands.size());
	}

	/**
	 * Returns command object with the given name.
	 * 
	 * @param commandName
	 *            Name of the command.
	 * @return Command object.
	 */
	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			LOG.trace("Command not found, name --> " + commandName);
			return commands.get("noCommand");
		}

		return commands.get(commandName);
	}

}