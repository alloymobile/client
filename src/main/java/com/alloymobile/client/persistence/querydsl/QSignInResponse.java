package com.alloymobile.client.persistence.querydsl;

import com.alloymobile.client.persistence.model.Address;
import com.alloymobile.client.persistence.model.Role;
import com.alloymobile.client.persistence.model.SignInResponse;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.ListPath;
import com.querydsl.core.types.dsl.StringPath;

import javax.annotation.processing.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QSignInResponse is a Querydsl query type for SignInResponse
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSignInResponse extends EntityPathBase<SignInResponse> {

    private static final long serialVersionUID = 1752410156L;

    public static final QSignInResponse signInResponse = new QSignInResponse("signInResponse");

    public final QClient _super = new QClient(this);

    //inherited
    public final ListPath<Address, QAddress> addresses = _super.addresses;

    //inherited
    public final StringPath email = _super.email;

    //inherited
    public final StringPath firstName = _super.firstName;

    //inherited
    public final StringPath id = _super.id;

    //inherited
    public final StringPath lastName = _super.lastName;

    //inherited
    public final StringPath password = _super.password;

    //inherited
    public final StringPath phone = _super.phone;

    //inherited
    public final ListPath<Role, QRole> roles = _super.roles;

    public final StringPath token = createString("token");

    public QSignInResponse(String variable) {
        super(SignInResponse.class, forVariable(variable));
    }

    public QSignInResponse(Path<? extends SignInResponse> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSignInResponse(PathMetadata metadata) {
        super(SignInResponse.class, metadata);
    }

}

