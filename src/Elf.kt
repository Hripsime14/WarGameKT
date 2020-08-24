class Elf: Mystic(800, 10, 5, "Elf") {
    private var HEALTH: Int = 0

    init {
        HEALTH = this.health
    }

    fun getInitialHealth() : Int{
        return HEALTH
    }
}