package com.slaw.howifeel.component

import javax.inject.Scope

/**
 * Dagger injection scope for items that have a lifetime of no longer than a given activity.
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope