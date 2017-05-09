% Filename: uml.mp
input metauml;
beginfig(1);
    Interface.environment("Environment")
    ("+restart()",
    "+act()",
    "+getScreen()",
    "+isTerminal()",
    "+getAction()");
    drawObjects(environment);

    Class.aleEnvironment("ALE Environment")("+ale")
    ("+restart()",
    "+act()",
    "+getScreen()",
    "+isTerminal()",
    "+getAction()");
    topToBottom(100)(environment, aleEnvironment);
    drawObjects(aleEnvironment);
    clink(inheritance)(aleEnvironment, environment);

    Class.agent("Agent")(
    "+env",
    "+plotter",
    "+mem",
    "+callback",
    "+buf",
    "+dqn",
    "+action",
    "+explorerate",
    "+train"
    )(
    "+step()",
    "+playrand()",
    "+train()",
    "+test()",
    "+play()"
    );

    leftToRight(90)(environment, agent);


    Class.plotter("Plotter")("values")
    ("+updateValues()");
    leftToRight(30)(agent, plotter);

    Class.statistics("Statistics")(
        "+csvfile",
        "+net",
        "+agent"
    )(
        "+reset()",
        "+on_step()",
        "+on_train()",
        "+write()",
        "+close()"
    );
    leftToRight(30)(aleEnvironment, statistics);


    Class.dqn("DeepQNetwork")(
        "+action",
        "+batchSize",
        "+minReward",
        "+maxReward",
        "+backend"
    )(
        "+update network()",
        "+train()",
        "+predict()",
        "+load()",
        "+save()"
    );
    leftToRight(30)(statistics, dqn);

    Class.mry("ReplayMemory")(
        "+actions",
        "+rewards",
        "+screens"
    )(
        "+add()",
        "+getState()"
    );
    leftToRight(30)(dqn, mry);

    Class.sbuf("StateBuffer")(
        "+buffer"
    )(
        "+add()",
        "+getState()",
        "+getStateMinibatch()",
        "+reset()"
    );
    leftToRight(30)(mry, sbuf);

    drawObjects(agent, plotter, dqn, mry, sbuf, statistics);

    clink(associationUni)(agent, plotter);
    clink(associationUni)(agent, dqn);
    clink(associationUni)(agent, environment);
    clink(associationUni)(agent, mry);
    clink(associationUni)(agent, sbuf);
    clink(associationUni)(agent, statistics);
endfig;
end
