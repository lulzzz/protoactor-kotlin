package proto.actor

typealias Partition = MutableMap<String, Process>

internal class HashedConcurrentDictionary {
    companion object {
        private val HashSize: Int = 1024
        /*fun calculateHash (read : String) : Long {
            var hashedValue : Long = 3074457345618258791
            for (i in 0..read.length) {
                hashedValue += read[i].toInt()
                hashedValue *= 3074457345618258799
            }
            return hashedValue
        }*/
    }

    private val partitions: Array<Partition> = Array(HashSize, { _ -> mutableMapOf<String, Process>() })
    private fun getPartition(key: String): Partition {
        val hash: Int = Math.abs(key.hashCode()) % HashSize
        val p = partitions[hash]
        return p
    }

    fun tryAdd(key: String, reff: Process): Boolean {
        val p = getPartition(key)
        synchronized(p, {
            if (p.containsKey(key)) {
                return false
            }
            p.put(key, reff)
            return true
        })
    }

    fun tryGetValue(key: String): Process? {
        val p = getPartition(key)
        synchronized(p, {
            return p[key]
        })
    }

    fun remove(key: String) {
        val p = getPartition(key)
        synchronized(p, {
            p.remove(key)
        })
    }
}
