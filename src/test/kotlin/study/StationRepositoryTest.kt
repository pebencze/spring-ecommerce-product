package study

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.jdbc.core.simple.JdbcClient

@DataJpaTest
class StationRepositoryTest {

    @Autowired
    private lateinit var jdbcClient: JdbcClient

    @Autowired
    private lateinit var stations: StationRepository

    @Test
    fun save() {
        val expected = Station(name = "pankow")
        val actual = stations.save(expected)
        assertThat(actual.id).isNotZero()
        assertThat(actual.name).isEqualTo(expected.name)
    }

    @Test
    fun findByName() {
        val expected = "pankow"
        stations.save(Station(name = expected))
        val actual = stations.findByName(expected)?.name
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun identity() {
        val station1 = stations.save(Station(name = "pankow"))
        val station2 = stations.findById(station1.id).get()
        assertThat(station1 === station2).isTrue()
        assertThat(station1).isEqualTo(station2)
        assertThat(station1).isSameAs(station2)
    }

    @Test
    fun test1(){
        jdbcClient.sql("insert into stations (name, id) values ('mitte', 3L)")

        val actual = stations.findById(3L).get()

        assertThat(actual.id).isEqualTo(3L)
        assertThat(actual.name).isEqualTo("mitte")
    }

    @Test
    fun `saves only in cache, updates DB after flush`(){
        val station1 = stations.save(Station("pankow")) // insert
        station1.changeName("oranienburger") // update only in cache, no SQL
        val station2 = stations.findById(1L)
        assertThat(station2).isNotNull()
        stations.flush() // update in DB
    }

    @Test
    fun `use only one insert, no update`(){
        val station1 = stations.save(Station("pankow")) // insert
        station1.changeName("oranienburger") // update only in cache, no SQL
        station1.changeName("pankow") // update only in cache, no SQL
        stations.flush() // do NOT update!
    }



}
