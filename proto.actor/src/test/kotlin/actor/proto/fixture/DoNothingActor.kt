package actor.proto.fixture

import actor.proto.Actor
import actor.proto.Context

class DoNothingActor : Actor {
    suspend override fun receive (context : Context) {}
}

