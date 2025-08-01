package study;

import jakarta.persistence.*;

@Entity
@Table(name = "line")
class Line(
    @Column(name = "name", nullable = false)
    var name: String,

    @OneToMany
    @JoinColumn(name = "line_id")
    var stations: MutableList<Station> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) {
    fun addStation(station: Station) {
        stations.add(station)
        station.line = this
    }
}
