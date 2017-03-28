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

    leftToRight(30)(environment, agent);


    Class.plotter("Plotter")("values")
    ("+updateValues()");
    leftToRight(30)(agent, plotter);

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
    leftToRight(30)(aleEnvironment, dqn);

    Class.mry("ReplayMemory")(
        "+actions",
        "+rewards",
        "+screens"
    )(
        "+add()",
        "+getState()"
    );
    leftToRight(30)(dqn, mry);

    drawObjects(agent, plotter, dqn, mry);

    clink(associationUni)(agent, plotter);
    clink(associationUni)(agent, dqn);
    clink(associationUni)(agent, environment);
    clink(associationUni)(agent, mry);
endfig;
end
