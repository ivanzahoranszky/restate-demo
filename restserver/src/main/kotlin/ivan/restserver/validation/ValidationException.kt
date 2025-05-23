package ivan.restserver.validation

import java.lang.RuntimeException

class ValidationException(message: String): RuntimeException(message)