open class Coordinates(x: Double, y: Double) {
    private var x: Double? = null
    private var y: Double? = null

    init {
        this.x = x
        this.y = y
    }

    fun getX(): Double? { // TODO: ba inchi es depqum chkar automat getterner?
        return x
    }

    fun getY(): Double? {
        return y
    }


}