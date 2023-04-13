package nomic.data.repositories

/**
 * Exception to throw when 404 is thrown from the Nomic API
 *
 * @return Exception the response for the user to know that the data they were looking for was not found
 */
class EntityNotFoundException : Exception("Nomic API was unable to find the data requested")