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
    fun `transient (in cache) line should have id of 0 without cascade`() {
        val line = Line(name = "Line1")
        val station = Station(name = "Pankow", line = line)
        val actual = stations.save(station)
        assertThat(actual.id).isNotZero()
        assertThat(actual.id).isNotNull()
        assertThat(actual.line?.id).isZero()
        assertThat(actual.line?.name).isEqualTo(line.name)
        lines.save(line)
        assertThat(actual.line?.id).isEqualTo(1L)
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

    @Test
    fun `transient (in cache) line should have id of 1 with cascade`() {
        val line = Line(name = "Line1")
        val station = Station(name = "Pankow", line = line)
        val actual = stations.save(station)
        assertThat(actual.id).isNotZero()
        assertThat(actual.id).isNotNull()
        assertThat(actual.line?.id).isEqualTo(1L)
    }

    @Test
    fun `update with line`() {
        val expected = stations.findByName("pankow")
        val line1 = Line(name = "Line1")
        expected?.line = line1
        stations.flush()
    }

    @Test
    fun findById() {
        val line = lines.findByName("line1")
        assertThat(line.stations).hasSize(1)
    }

    @Test
    fun save() {
        val expected = Line("line1")
        expected.addStation(Station("pankow"))
        lines.save(expected)
        lines.flush() // transaction commit
    }





}
