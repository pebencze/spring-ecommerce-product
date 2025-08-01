package study

import jakarta.persistence.*

@Entity
@Table(name = "station")
class Station(
    @Column(name = "station_name", nullable = false)
    var name: String,
    
    @Id // (4)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @ManyToOne(cascade = [(CascadeType.PERSIST)])
    @JoinColumn(name = "line_id")
    var line: Line? = null,
) {
    fun changeName(name: String) {
        this.name = name
    }

    fun updateLine(line: Line) {
        this.line = line
        line.stations.add(this)
    }
}
