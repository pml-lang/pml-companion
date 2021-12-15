// SPDX-License-Identifier: GPL-2.0-only
// Copyright (C) 2018 - 2021 Christian Neumanns, email: chris@pml-lang.dev

package dev.pml.converter.data.user_defined_node;

import dev.pp.text.annotations.NotNull;

public record UserDefinedNodeWriter(
    @NotNull String targetCodeName,
    @NotNull String scriptLanguage,
    @NotNull String script ) {}
