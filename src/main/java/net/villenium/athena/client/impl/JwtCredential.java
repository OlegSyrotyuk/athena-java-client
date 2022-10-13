package net.villenium.athena.client.impl;

import io.grpc.CallCredentials;
import io.grpc.Metadata;
import io.grpc.Status;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import net.villenium.athena.client.util.Constant;

import java.util.concurrent.Executor;

@RequiredArgsConstructor
public class JwtCredential extends CallCredentials {

    private final String subject;
    private final String token;

    @Override
    public void applyRequestMetadata(final RequestInfo requestInfo, final Executor executor,
                                     final MetadataApplier metadataApplier) {
        final String jwt =
                Jwts.builder()
                        .setSubject(subject)
                        .signWith(SignatureAlgorithm.HS256, token)
                        .compact();

        executor.execute(() -> {
            try {
                Metadata headers = new Metadata();
                headers.put(Constant.AUTHORIZATION_METADATA_KEY,
                        String.format("%s %s", Constant.BEARER_TYPE, jwt));
                metadataApplier.apply(headers);
            } catch (Throwable e) {
                metadataApplier.fail(Status.UNAUTHENTICATED.withCause(e));
            }
        });
    }

    @Override
    public void thisUsesUnstableApi() {
    }
}
