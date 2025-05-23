package ivan.restserver.validation

interface Validator<T> {

    suspend fun validate(toBeValidated: T)

}