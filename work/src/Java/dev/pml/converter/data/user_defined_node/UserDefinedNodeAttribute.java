// SPDX-License-Identifier: GPL-2.0-only
// Copyright (C) 2018 - 2021 Christian Neumanns, email: chris@pml-lang.dev

package dev.pml.converter.data.user_defined_node;

import dev.pp.text.annotations.NotNull;
import dev.pp.text.annotations.Nullable;

public record UserDefinedNodeAttribute(
    @NotNull String name,
    @Nullable String defaultValue ) {}
