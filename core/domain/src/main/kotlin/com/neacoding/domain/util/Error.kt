package com.neacoding.domain.util

/**
 * Marker for every typed error in the app. Data, domain and validation errors all
 * implement it so they can flow through [Result] without ever throwing.
 */
interface Error
