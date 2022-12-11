package com.github.fwfurtado.tinyurl.datasource.cache;

public record BloomFilterInfo(
        long capacity,
        long size,
        long filters,
        long items,
        long expansionRate
) {
}
