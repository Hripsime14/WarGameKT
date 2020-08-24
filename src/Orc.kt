class Orc: Mystic(1200, 15, 1, "Orc") {
    private var HEALTH: Int = 0

    init {
        HEALTH = this.health
    }

    fun getInitialHealth() : Int{
        return HEALTH
    }
}