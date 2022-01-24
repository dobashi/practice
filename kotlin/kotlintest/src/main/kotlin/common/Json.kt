package common

import com.fasterxml.jackson.databind.ObjectMapper

interface JsonObjectMapper {
    fun jacksonObjectMapper() = ObjectMapper()
}