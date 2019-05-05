package me.mqueiroz.github.utils.schedulers

import io.reactivex.schedulers.TestScheduler

class TestSchedulerProvider(private val scheduler: TestScheduler) :
    SchedulerProvider {
    override fun computation() = scheduler
    override fun ui() = scheduler
    override fun io() = scheduler
}
