package com.example.radmintool

import com.example.radmintool.utils.DatabasePreparatorUtils
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener
import org.springframework.transaction.annotation.EnableTransactionManagement


@SpringBootApplication
@EntityScan("com.example.radmintool")
//@ComponentScan("com.example.radmintool")
@EnableTransactionManagement
class RadmintoolBackendApplication(
        private val databasePreparatorUtils: DatabasePreparatorUtils
) {

    @EventListener(ApplicationReadyEvent::class)
    fun fireEventAfterStart() {
        databasePreparatorUtils.addPeopleToDb()
    }


}

fun main(args: Array<String>) {
    runApplication<RadmintoolBackendApplication>(*args)
}
