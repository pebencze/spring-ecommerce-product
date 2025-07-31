package study

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class LineRepositoryTest {

    @Autowired
    private lateinit var lines: LineRepository

    @Autowired
    private lateinit var stations: StationRepository

    @Test
    fun `transient (in cache) line should have id of 0`() {
        val line = Line(name = "Line1")
        val station = Station(name = "Pankow", line = line)
        val actual = stations.save(station)
        assertThat(actual.id).isNotZero()
        assertThat(actual.id).isNotNull()
        assertThat(actual.line?.id).isZero()
    }

    @Test
    fun `persistent (in db) line should have id of 1`() {
        val line = Line(name = "Line1")
        val station = Station(name = "Pankow", line = lines.save(line))
        val actual = stations.save(station)
        assertThat(actual.id).isNotZero()
        assertThat(actual.id).isNotNull()
        assertThat(actual.line?.id).isNotZero()
    }

}
