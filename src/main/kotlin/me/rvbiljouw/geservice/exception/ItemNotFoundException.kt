package me.rvbiljouw.geservice.exception

/**
 * @author rvbiljouw
 */
open class ItemNotFoundException(id: Short) : Exception("Item #$id not found!")
