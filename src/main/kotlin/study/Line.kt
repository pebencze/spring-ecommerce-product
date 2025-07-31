package study;

import jakarta.persistence.*;

@Entity
@Table(name = "line")
class Line(
    @Column(name = "name", nullable = false)
    var name: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
)
