class Commando: Human(850, 12, 4, "Commando") {
    private var HEALTH: Int = 0

    init {
        HEALTH = this.health
    }

    fun getInitialHealth() : Int{
        return HEALTH
    }
}