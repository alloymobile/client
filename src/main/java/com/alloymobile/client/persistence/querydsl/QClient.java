package com.alloymobile.client.persistence.querydsl;

import com.alloymobile.client.persistence.model.Address;
import com.alloymobile.client.persistence.model.Client;
import com.alloymobile.client.persistence.model.Role;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.ListPath;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.core.types.dsl.StringPath;

import javax.annotation.processing.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QClient is a Querydsl query type for Client
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClient extends EntityPathBase<Client> {

    private static final long serialVersionUID = 2034610900L;

    public static final QClient client = new QClient("client");

    public final ListPath<Address, QAddress> addresses = this.<Address, QAddress>createList("addresses", Address.class, QAddress.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final StringPath firstName = createString("firstName");

    public final StringPath id = createString("id");

    public final StringPath lastName = createString("lastName");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final ListPath<Role, QRole> roles = this.<Role, QRole>createList("roles", Role.class, QRole.class, PathInits.DIRECT2);

    public QClient(String variable) {
        super(Client.class, forVariable(variable));
    }

    public QClient(Path<? extends Client> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClient(PathMetadata metadata) {
        super(Client.class, metadata);
    }

}

