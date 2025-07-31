package study

import org.springframework.data.jpa.repository.JpaRepository

interface StationRepository : JpaRepository<Station, Long> {
    fun findByName(name: String): Station?
}
