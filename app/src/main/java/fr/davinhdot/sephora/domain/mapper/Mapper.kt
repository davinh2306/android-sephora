package fr.davinhdot.sephora.domain.mapper

/**
 * Interface for model mappers. It provides helper methods that facilitate
 * retrieving of models from outer data source layers
 */
interface Mapper<I, O> {

    fun map(input: I, vararg params: String): O
}

// Non-nullable to Non-nullable
interface ListMapper<I, O> : Mapper<List<I>, List<O>>

class ListMapperImpl<I, O>(private val mapper: Mapper<I, O>) : ListMapper<I, O> {

    override fun map(input: List<I>, vararg params: String): List<O> {
        return input.map { mapper.map(it) }
    }
}

// Nullable to Non-nullable
interface NullableInputListMapper<I, O> : Mapper<List<I>?, List<O>>

class NullableInputListMapperImpl<I, O>(private val mapper: Mapper<I, O>) :
    NullableInputListMapper<I, O> {

    override fun map(input: List<I>?, vararg params: String): List<O> {
        return input?.map { mapper.map(it) }.orEmpty()
    }
}

// Non-nullable to Nullable
interface NullableOutputListMapper<I, O> : Mapper<List<I>, List<O>?>

class NullableOutputListMapperImpl<I, O>(private val mapper: Mapper<I, O>) :
    NullableOutputListMapper<I, O> {

    override fun map(input: List<I>, vararg params: String): List<O>? {
        return if (input.isEmpty()) null else input.map { mapper.map(it) }
    }
}